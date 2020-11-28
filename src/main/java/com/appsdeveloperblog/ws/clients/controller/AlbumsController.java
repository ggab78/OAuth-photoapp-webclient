package com.appsdeveloperblog.ws.clients.controller;

import com.appsdeveloperblog.ws.clients.response.AlbumRest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AlbumsController {

    @GetMapping("/albums")
    public String getAlbums(Model model){

        List<AlbumRest> albums=new ArrayList<>();

        AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build();
        albums.add(AlbumRest.builder().albumTitle("first").albumUrl("http://album1").build());
        albums.add(AlbumRest.builder().albumTitle("second").albumUrl("http://album2").build());

        model.addAttribute("albums", albums);

        return "albums";
    }

}
