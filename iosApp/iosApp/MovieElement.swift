//
//  MovieElement.swift
//  iosApp
//
//  Created by Roberto Orgiu on 06/08/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI

struct MovieElement : View {
    
    var movie: NativeMovie
    
    var body: some View {
        VStack {
            Text(movie.title)
                .font(.title)
                .foregroundColor(Color.blue)
                .bold()
            Spacer()
            Text(movie.plot)
                .font(.subheadline)
            Spacer()
        }.padding()
    }
}

struct MovieElement_Previews: PreviewProvider {
    static var previews: some View {
        MovieElement(movie: NativeMovies().movies[0])
    }
}
