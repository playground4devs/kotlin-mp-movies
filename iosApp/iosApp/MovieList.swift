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
    
    let nativeMovies = NativeMovies()
    
    @State private var toggle = false
    
    var body: some View {
        VStack {
            Button("Refresh", action: { self.toggle.toggle() } )
            
            Spacer()
            
            if toggle {
                list
            }

        }
    }
    
    private var list: some View {
        if nativeMovies.apolloMovies.isEmpty {
            NSLog("Using memory movies")
            return List(nativeMovies.movies){movie in
                MovieElement(movie: movie)
            }
        } else {
            NSLog("Using Apollo movies")
            return List(nativeMovies.apolloMovies){movie in
                MovieElement(movie: movie)
            }
        }
    }

}

struct MovieList_Previews : PreviewProvider {
    static var previews: some View {
        MovieList()
    }
}
