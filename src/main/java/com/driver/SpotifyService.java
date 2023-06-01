package com.driver;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    //Auto-wire will not work in this case, no need to change this and add autowire

    SpotifyRepository spotifyRepository = new SpotifyRepository();

    public User createUser(String name, String mobile){
       return spotifyRepository.createUser(name,mobile);
    }

    public Artist createArtist(String name) {
        return spotifyRepository.createArtist(name);
    }

    public Album createAlbum(String title, String artistName) {
        List<Artist> artists=spotifyRepository.getArtists();
        boolean flag=false;
        for(Artist artist:artists){
            if(artist.getName().equals(artistName)){
                flag=true;
                break;
            }
        }
        Album album;
        if (!flag) {
            spotifyRepository.createArtist(artistName);
        }
        album= spotifyRepository.createAlbum(title,artistName);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        List<Album> albums=spotifyRepository.getAlbums();
        boolean flag=false;
        for (Album album:albums){
            if (album.getTitle().equals(albumName)){
                flag=true;
            }
        }
        if (flag==false){
            throw new RuntimeException("Album does not exist");
        }

        Song song= spotifyRepository.createSong(title,albumName,length);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        Playlist playlist=spotifyRepository.createPlaylistOnLength(mobile,title,length);
        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        Playlist playlist=spotifyRepository.createPlaylistOnName(mobile,title,songTitles);
        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

        Playlist playlist=spotifyRepository.findPlaylist(mobile,playlistTitle);
        return playlist;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        Song song=spotifyRepository.likeSong(mobile,songTitle);
        return song;
    }

    public String mostPopularArtist() {
        return spotifyRepository.mostPopularArtist();
    }

    public String mostPopularSong() {
        return spotifyRepository.mostPopularSong();
    }
}
