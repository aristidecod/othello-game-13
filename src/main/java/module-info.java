module fr.univ_amu.m1info.board_game_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports fr.univ_amu.m1info.board_game_library.graphics;
    exports fr.univ_amu.m1info.board_game_library;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.app;
    exports fr.univ_amu.m1info.board_game_library.graphics.configuration;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.view;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.bar;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.board;
    exports fr.univ_amu.m1info.board_game_library.iterator;
    exports fr.univ_amu.m1info.board_game_library.command;
    exports fr.univ_amu.m1info.board_game_library.controller;
    exports fr.univ_amu.m1info.board_game_library.view;
    exports fr.univ_amu.m1info.board_game_library.model;
}