package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {

        users.add(new User(name, mobile));
        return users.get(users.size() - 1);
    }
    public Artist createArtist(String name) {
        Artist artist=new Artist(name);
        artists.add(new Artist(name));
       return artists.get(artists.size() - 1);
    }

    public HashMap<Artist, List<Album>> getArtistAlbumMap() {
        return artistAlbumMap;
    }

    public void setArtistAlbumMap(HashMap<Artist, List<Album>> artistAlbumMap) {
        this.artistAlbumMap = artistAlbumMap;
    }

    public HashMap<Album, List<Song>> getAlbumSongMap() {
        return albumSongMap;
    }

    public void setAlbumSongMap(HashMap<Album, List<Song>> albumSongMap) {
        this.albumSongMap = albumSongMap;
    }

    public HashMap<Playlist, List<Song>> getPlaylistSongMap() {
        return playlistSongMap;
    }

    public void setPlaylistSongMap(HashMap<Playlist, List<Song>> playlistSongMap) {
        this.playlistSongMap = playlistSongMap;
    }

    public HashMap<Playlist, List<User>> getPlaylistListenerMap() {
        return playlistListenerMap;
    }

    public void setPlaylistListenerMap(HashMap<Playlist, List<User>> playlistListenerMap) {
        this.playlistListenerMap = playlistListenerMap;
    }

    public HashMap<User, Playlist> getCreatorPlaylistMap() {
        return creatorPlaylistMap;
    }

    public void setCreatorPlaylistMap(HashMap<User, Playlist> creatorPlaylistMap) {
        this.creatorPlaylistMap = creatorPlaylistMap;
    }

    public HashMap<User, List<Playlist>> getUserPlaylistMap() {
        return userPlaylistMap;
    }

    public void setUserPlaylistMap(HashMap<User, List<Playlist>> userPlaylistMap) {
        this.userPlaylistMap = userPlaylistMap;
    }

    public HashMap<Song, List<User>> getSongLikeMap() {
        return songLikeMap;
    }

    public void setSongLikeMap(HashMap<Song, List<User>> songLikeMap) {
        this.songLikeMap = songLikeMap;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Album createAlbum(String title, String artistName) {
        Album album=new Album(title);
        albums.add(album);
        Artist artist1 = null;
        for (Artist artist:artists){
            if (artist.getName().equals(artistName)){
                artist1=artist;
                break;
            }
        }
        List<Album> albumList=artistAlbumMap.getOrDefault(artist1,new ArrayList<>());
        albumList.add(album);
        artistAlbumMap.put(artist1,albumList);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Song song=new Song(title,length);
        songs.add(song);
        Album album=null;
        for (Album album1:albums){
            if(album1.getTitle().equals(albumName)){
                album=album1;
                break;
            }
        }
        List<Song> songList=albumSongMap.getOrDefault(album,new ArrayList<>());
        songList.add(song);
        albumSongMap.put(album,songList);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        boolean flag=false;
        User user1=null;
        for (User user:users){
            if(user.getMobile().equals(mobile)){
                flag=true;
                user1=user;
                break;
            }
        }
        if (flag==false){
            throw new Exception("User does not exist");
        }
        Playlist playlist= null;
        boolean flag1=false;
        for (Playlist playlist1:playlists){
            if (playlist1.getTitle().equals(title)){
                playlist=playlist1;
                flag1=true;
                break;
            }
        }
        if (flag1==false)playlist=new Playlist();
        if (!playlists.contains(playlist))playlists.add(playlist);

        List<Song> songList=new ArrayList<>();
        for (Song song:songs){
            if (song.getLength()==length){
                songList.add(song);
            }
        }
        playlistSongMap.put(playlist,songList);
        creatorPlaylistMap.put(user1,playlist);
        List<Playlist> playlistList=userPlaylistMap.getOrDefault(user1,new ArrayList<>());
        playlistList.add(playlist);
        userPlaylistMap.put(user1,playlistList);
        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        boolean flag=false;
        User user1=null;
        for (User user:users){
            if(user.getMobile().equals(mobile)){
                flag=true;
                user1=user;
                break;
            }
        }
        if (flag==false){
            throw new Exception("User does not exist");
        }
        Playlist playlist= null;
        boolean flag1=false;
        for (Playlist playlist1:playlists){
            if (playlist1.getTitle().equals(title)){
                playlist=playlist1;
                flag1=true;
                break;
            }
        }
        if (flag1==false)playlist=new Playlist();
        if (!playlists.contains(playlist))playlists.add(playlist);

        List<Song> songList=new ArrayList<>();
        for (Song song:songs){
            if (songTitles.contains(song.getTitle())){
                songList.add(song);
            }
        }
        playlistSongMap.put(playlist,songList);
        creatorPlaylistMap.put(user1,playlist);
        userPlaylistMap.put(user1,userPlaylistMap.getOrDefault(user1,new ArrayList<>())).add(playlist);
        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        boolean userFlag=false;
        User user=null;
        for (User ur:users){
            if(ur.getMobile().equals(mobile)){
                userFlag=true;
                user=ur;
                break;
            }
        }
        if(userFlag==false){
            throw  new Exception("User does not exist");
        }
        boolean playListFlag=false;
        Playlist playlist=null;
        for (Playlist playlist1:playlists){
            if(playlist1.getTitle().equals(playlistTitle)){
                playListFlag=true;
                playlist=playlist1;
            }
        }
        if(playListFlag==false){
            throw  new Exception("Playlist does not exist");
        }

        if(creatorPlaylistMap.containsKey(user) && creatorPlaylistMap.get(user).getTitle().equals(playlistTitle)){
            return playlist;
        }
        if(userPlaylistMap.containsKey(user) && userPlaylistMap.get(user).contains(playlist)){
            return playlist;
        }
        userPlaylistMap.put(user,userPlaylistMap.getOrDefault(user,new ArrayList<>())).add(playlist);
        playlistListenerMap.put(playlist,playlistListenerMap.getOrDefault(playlist,new ArrayList<>())).add(user);
        return playlist;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        boolean userFlag=false;
        User user=null;
        for (User user1:users){
            if (user1.getMobile().equals(mobile)){
                user=user1;
                userFlag=true;
            }
        }
        if(userFlag==false){
            throw  new Exception("User does not exist");
        }

        boolean songFlag=false;
        Song song=null;
        for (Song song1:songs){
            if (song1.getTitle().equals(songTitle)){
                song=song1;
                songFlag=true;
            }
        }
        if(songFlag==false){
            throw  new Exception("Song does not exist");
        }

        if (songLikeMap.containsKey(song) && songLikeMap.get(song).contains(user)){
            return song;
        }
        List<User> userList=songLikeMap.getOrDefault(song,new ArrayList<>());
        userList.add(user);
        songLikeMap.put(song,userList);

        Album album1=null;
        for ( Album album: albumSongMap.keySet()){
            List<Song> songList=albumSongMap.get(album);
            if(songList.contains(song)){
                album1=album;
                break;
            }
        }

        Artist artist=null;
        for (Artist artist1:artistAlbumMap.keySet()){
            List<Album> albumList=artistAlbumMap.get(artist1);
            if(albumList.contains(album1)){
                artist=artist1;
                break;
            }
        }
        song.setLikes(song.getLikes()+1);
        artist.setLikes(artist.getLikes()+1);
        return song;
    }

    public String mostPopularArtist() {
        int max=Integer.MIN_VALUE;
        String ans="";
        for (Artist artist:artists){
            if(artist.getLikes()>max){
                max=artist.getLikes();
                ans=artist.getName();
            }
        }
        return  ans;
    }

    public String mostPopularSong() {
        int max=Integer.MIN_VALUE;
        String ans="";
        for (Song song:songLikeMap.keySet()){
            if(max<songLikeMap.get(song).size()){
                ans=song.getTitle();
                max=songLikeMap.get(song).size();
            }
        }
        return ans;
    }
}