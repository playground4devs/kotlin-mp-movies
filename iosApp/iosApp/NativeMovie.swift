//
//  NativeMovie.swift
//  iosApp
//
//  Created by Roberto Orgiu on 06/08/2020.
//  Copyright © 2020 orgName. All rights reserved.
//
import SwiftUI
import shared

struct NativeMovie: Identifiable {
    var id = UUID()
    
    var title: String
    var plot: String
}

class NativeMovies {
    var movies = ModelSamples().movies.map { (Movie) -> NativeMovie in
        NativeMovie(title: Movie.title, plot: Movie.plot)
    }
}
