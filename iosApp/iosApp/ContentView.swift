import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        List {
            Text(Proxy().proxyHello())
            Text(Platform().name)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
