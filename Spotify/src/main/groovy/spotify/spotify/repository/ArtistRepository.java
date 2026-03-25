package spotify.spotify.repository;

import spotify.spotify.JdbcUtils;
import spotify.spotify.domain.Artist;
import spotify.spotify.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArtistRepository implements AbstractRepository<Long,Artist>{
    private JdbcUtils dbUtils;

    public ArtistRepository(Properties props) {
        this.dbUtils = new  JdbcUtils(props);
    }

    @Override
    public void add(Artist entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep=conn.prepareStatement("insert into artisti (nume_scena) values (?)"))
        {
            prep.setString(1, entity.getNume_scena());
            prep.execute();
        }catch (SQLException s){
            throw new RepositoryException("DB error: " +s.getMessage());
        }

    }

    @Override
    public void delete(Long aLong) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep = conn.prepareStatement("delete from melodii_artisti where id_artist = ?");
            PreparedStatement prep2 = conn.prepareStatement("delete from artisti where id = ?"))
        {
            prep.setLong(1, aLong);
            prep2.setLong(1, aLong);

            prep.execute();
            prep2.execute();
        }catch (SQLException s){
            throw new RepositoryException("DB error: " +s.getMessage());
        }
    }

    @Override
    public void update(Artist entity) {
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement prep = conn.prepareStatement("UPDATE artisti SET nume_scena = ? WHERE id = ?"))
        {
            prep.setString(1, entity.getNume_scena());
            prep.setLong(2,entity.getId());
            prep.execute();
        }catch(SQLException s){
            throw new RepositoryException("DB error: " +s.getMessage());
        }
    }

    @Override
    public Artist findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        try(Connection connection =dbUtils.getConnection();
            PreparedStatement stmt=connection.prepareStatement("select * from artisti");
            ResultSet rs=stmt.executeQuery())
        {
            while (rs.next()){
                Long id =  rs.getLong("id");
                String nume_scena=rs.getString("nume_scena");
                Artist artist=new Artist(nume_scena);
                artist.setId(id);
                artists.add(artist);
            }
        }catch (SQLException s){
            throw new RepositoryException("DB error: " + s.getMessage());
        }
        return artists;
    }
}
