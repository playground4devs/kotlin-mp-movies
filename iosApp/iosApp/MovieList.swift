//
//  MovieList.swift
//  iosApp
//
//  Created by Roberto Orgiu on 06/08/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MovieList : View {
    
    var body: some View {
        List(NativeMovies().movies){movie in
            MovieElement(movie: movie)
        }
    }
}

struct MovieList_Previews : PreviewProvider {
    static var previews: some View {
        MovieList()
    }
}
