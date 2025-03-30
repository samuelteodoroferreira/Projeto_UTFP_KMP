import SwiftUI
import shared

@main
struct ConsultaCEPApp: App {
    init() {
        // Configurações globais da aplicação
        UITableView.appearance().backgroundColor = .clear
        UINavigationBar.appearance().largeTitleTextAttributes = [
            .foregroundColor: UIColor(hex: "009C3B")
        ]
        UINavigationBar.appearance().tintColor = UIColor(hex: "009C3B")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
} 