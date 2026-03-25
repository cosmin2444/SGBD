package spotify.spotify.repository;

import spotify.spotify.JdbcUtils;
import spotify.spotify.domain.Album;
import spotify.spotify.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AlbumRepository implements AbstractRepository<Long, Album>{

    private JdbcUtils dbUtils;

    public AlbumRepository(Properties prop){
        dbUtils = new JdbcUtils(prop);
    }

    @Override
    public void add(Album entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("INSERT INTO albume(denumire,an_aparitie) VALUES(?,?)"))
        {
            prep.setString(1,entity.getDenumire());
            prep.setInt(2,entity.getAn_aparitie());
            prep.executeUpdate();

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
    }

    @Override
    public void delete(Long aLong) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep = conn.prepareStatement("UPDATE melodii SET id_album = ? WHERE id_album = ?");
            PreparedStatement prep2=conn.prepareStatement("DELETE FROM albume WHERE id=?"))
        {
            prep.setObject(1, null);
            prep.setLong(2,aLong);
            prep2.setLong(1,aLong);

            prep.executeUpdate();
            prep2.executeUpdate();

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
    }

    @Override
    public void update(Album entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep= conn.prepareStatement("UPDATE albume SET denumire=?,an_aparitie=? WHERE id=?"))
        {
            prep.setString(1,entity.getDenumire());
            prep.setInt(2,entity.getAn_aparitie());
            prep.setLong(3,entity.getId());
            prep.executeUpdate();

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
    }

    @Override
    public Album findOne(Long aLong) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("SELECT * FROM albume WHERE id=?")
        ){
            prep.setLong(1,aLong);
            ResultSet rs=prep.executeQuery();
            if(rs.next()){
                String denumire=rs.getString("denumire");
                int an_aparitie=rs.getInt("an_aparitie");
                Album album=new Album(denumire,an_aparitie);
                album.setId(aLong);
                return album;
            }

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
        return null;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albume = new ArrayList<>();
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("SELECT * FROM albume");
            ResultSet rs=prep.executeQuery())
        {
            while(rs.next()){
                Long id =  rs.getLong("id");
                String denumire = rs.getString("denumire");
                int an_aparitie = rs.getInt("an_aparitie");
                Album album = new Album(denumire,an_aparitie);
                album.setId(id);
                albume.add(album);
            }

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
        return albume;
    }
}
