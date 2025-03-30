import SwiftUI
import shared

struct CepScreen: View {
    @StateObject private var viewModel = CepViewModel()
    @State private var showError = false
    @State private var errorMessage = ""
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 16) {
                    // Bandeira do Brasil
                    BrazilFlag()
                        .frame(height: 100)
                        .clipShape(RoundedRectangle(cornerRadius: 8))
                        .padding(.bottom, 16)
                    
                    Text("Consulta CEP")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(Color(hex: "009C3B")) // BrazilGreen
                    
                    TextField("Digite o CEP", text: Binding(
                        get: { viewModel.uiState.cep },
                        set: { viewModel.onCepChanged(cep: $0) }
                    ))
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .disabled(!viewModel.uiState.isInputEnabled)
                    .keyboardType(.numberPad)
                    .textContentType(.none)
                    .autocapitalization(.none)
                    .onChange(of: viewModel.uiState.cep) { newValue in
                        // Limita a 8 dígitos
                        if newValue.count > 8 {
                            viewModel.onCepChanged(cep: String(newValue.prefix(8)))
                        }
                    }
                    
                    Button(action: {
                        Task {
                            do {
                                try await viewModel.buscarCep()
                            } catch {
                                errorMessage = error.localizedDescription
                                showError = true
                            }
                        }
                    }) {
                        if viewModel.uiState.isLoading {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle(tint: .white))
                        } else {
                            Text("Buscar")
                                .fontWeight(.semibold)
                        }
                    }
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(
                        viewModel.uiState.isSearchEnabled && !viewModel.uiState.isLoading ?
                        Color(hex: "009C3B") : Color(hex: "009C3B").opacity(0.6)
                    )
                    .foregroundColor(.white)
                    .cornerRadius(8)
                    .disabled(!viewModel.uiState.isSearchEnabled || viewModel.uiState.isLoading)
                    .shadow(radius: 6)
                    
                    if let cepInfo = viewModel.uiState.cepInfo {
                        VStack(spacing: 12) {
                            InfoRow(label: "CEP", value: cepInfo.cep)
                            InfoRow(label: "Logradouro", value: cepInfo.logradouro)
                            InfoRow(label: "Bairro", value: cepInfo.bairro)
                            InfoRow(label: "Cidade", value: cepInfo.localidade)
                            InfoRow(label: "UF", value: cepInfo.uf)
                        }
                        .padding()
                        .background(Color(hex: "FFDF00").opacity(0.1))
                        .cornerRadius(8)
                        .overlay(
                            RoundedRectangle(cornerRadius: 8)
                                .stroke(Color(hex: "FFDF00"), lineWidth: 1)
                        )
                        .shadow(radius: 4)
                        .transition(.opacity.combined(with: .scale))
                    }
                }
                .padding()
            }
            .background(Color(hex: "F5F5F5"))
            .alert("Erro", isPresented: $showError) {
                Button("OK", role: .cancel) {}
            } message: {
                Text(errorMessage)
            }
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct InfoRow: View {
    let label: String
    let value: String
    
    var body: some View {
        HStack {
            Text(label)
                .fontWeight(.bold)
                .foregroundColor(Color(hex: "006400")) // BrazilGreenDark
            Spacer()
            Text(value)
                .foregroundColor(.primary)
        }
    }
}

// Extensão para suportar cores hexadecimais
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }
        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}

struct BrazilFlag: View {
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                // Fundo verde
                Color(hex: "009C3B")
                
                // Losango amarelo
                Path { path in
                    let width = geometry.size.width
                    let height = geometry.size.height
                    path.move(to: CGPoint(x: width * 0.5, y: 0))
                    path.addLine(to: CGPoint(x: width, y: height * 0.5))
                    path.addLine(to: CGPoint(x: width * 0.5, y: height))
                    path.addLine(to: CGPoint(x: 0, y: height * 0.5))
                    path.closeSubpath()
                }
                .fill(Color(hex: "FFDF00"))
                
                // Círculo azul
                Circle()
                    .fill(Color(hex: "002776"))
                    .frame(width: geometry.size.width * 0.4)
                
                // Faixas brancas
                ForEach(0..<27) { i in
                    let angle = Double(i) * (360.0 / 27.0)
                    Rectangle()
                        .fill(Color.white)
                        .frame(width: geometry.size.width * 0.02, height: geometry.size.height * 0.5)
                        .offset(y: -geometry.size.height * 0.25)
                        .rotationEffect(.degrees(angle))
                }
                
                // Estrelas
                let stars = [
                    (0.5, 0.5, 0.15), // Centro
                    (0.2, 0.2, 0.08), // Norte
                    (0.8, 0.2, 0.08), // Nordeste
                    (0.8, 0.8, 0.08), // Sudeste
                    (0.2, 0.8, 0.08)  // Sul
                ]
                
                ForEach(0..<stars.count, id: \.self) { i in
                    let (x, y, size) = stars[i]
                    Star(points: 5, innerRatio: 0.4)
                        .fill(Color.white)
                        .frame(width: geometry.size.width * size)
                        .position(
                            x: geometry.size.width * x,
                            y: geometry.size.height * y
                        )
                }
            }
        }
    }
}

struct Star: Shape {
    let points: Int
    let innerRatio: CGFloat
    
    func path(in rect: CGRect) -> Path {
        let center = CGPoint(x: rect.midX, y: rect.midY)
        let outerRadius = min(rect.width, rect.height) / 2
        let innerRadius = outerRadius * innerRatio
        let angleStep = .pi * 2 / CGFloat(points * 2)
        
        var path = Path()
        
        for i in 0..<points * 2 {
            let angle = CGFloat(i) * angleStep - .pi / 2
            let radius = i % 2 == 0 ? outerRadius : innerRadius
            let point = CGPoint(
                x: center.x + cos(angle) * radius,
                y: center.y + sin(angle) * radius
            )
            
            if i == 0 {
                path.move(to: point)
            } else {
                path.addLine(to: point)
            }
        }
        
        path.closeSubpath()
        return path
    }
}

#Preview {
    CepScreen()
} 