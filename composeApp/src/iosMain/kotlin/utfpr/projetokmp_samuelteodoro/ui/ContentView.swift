import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        CepScreen()
            .preferredColorScheme(.light) // Força modo claro para manter as cores da bandeira
    }
}

#Preview {
    ContentView()
} 