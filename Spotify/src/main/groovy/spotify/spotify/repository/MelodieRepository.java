package spotify.spotify.repository;

import spotify.spotify.JdbcUtils;
import spotify.spotify.domain.Melodie;
import spotify.spotify.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MelodieRepository implements AbstractRepository<Long, Melodie>{

    private JdbcUtils dbUtils;

    public MelodieRepository(Properties prop){
        dbUtils = new JdbcUtils(prop);
    }

    @Override
    public void add(Melodie entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("INSERT INTO melodii(denumire,an_aparitie,id_album) VALUES(?,?,?)"))
        {
            prep.setString(1,entity.getDenumire());
            prep.setInt(2,entity.getAn_aparitie());
            prep.setLong(3,entity.getId_album());
            prep.executeUpdate();

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
    }

    @Override
    public void delete(Long aLong) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("DELETE FROM melodii_artisti WHERE id_melodie=?");
            PreparedStatement prep2=conn.prepareStatement("DELETE FROM melodii WHERE id=?")
        )
        {
            prep.setLong(1,aLong);
            prep2.setLong(1,aLong);

            prep.executeUpdate();
            prep2.executeUpdate();

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
    }

    @Override
    public void update(Melodie entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep= conn.prepareStatement("UPDATE melodii SET denumire=?,an_aparitie=? WHERE id=?"))
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
    public Melodie findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Melodie> findAll() {
        List<Melodie> melodii = new ArrayList<>();
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("SELECT * FROM melodii");
            ResultSet rs=prep.executeQuery())
        {
            createMelodie(melodii, rs);

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
        return melodii;
    }

    public List<Melodie> findAllForAlbum(Long id_album){
        List<Melodie> melodii = new ArrayList<>();
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("SELECT * FROM melodii WHERE id_album=?"))
        {
            prep.setLong(1,id_album);
            ResultSet rs=prep.executeQuery();
            createMelodie(melodii, rs);

        }catch (SQLException s){
            throw new RepositoryException("Db error: " + s.getMessage());
        }
        return melodii;
    }

    private void createMelodie(List<Melodie> melodii, ResultSet rs) throws SQLException {
        while(rs.next()){
            Long id =  rs.getLong("id");
            String denumire = rs.getString("denumire");
            int an_aparitie = rs.getInt("an_aparitie");
            Long id_album = rs.getLong("id_album");
            Melodie melodie = new Melodie(denumire,an_aparitie,id_album);
            melodie.setId(id);
            melodii.add(melodie);
        }
    }
}
