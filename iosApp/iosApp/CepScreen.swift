import SwiftUI
import Shared

/**
 * Tela principal de busca de CEP
 * @author Samuel Teodoro Ferreira
 */
struct CepView: View {
    @StateObject private var viewModel = CepViewModelWrapper()
    @State private var showingError = false
    
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                Text("Consulta CEP")
                    .font(.title)
                    .fontWeight(.bold)
                    .foregroundColor(.blue)
                
                TextField("Digite o CEP", text: $viewModel.cep)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .disabled(!viewModel.isInputEnabled)
                    .keyboardType(.numberPad)
                    .padding(.horizontal)
                
                Button(action: {
                    Task {
                        await viewModel.buscarCep()
                    }
                }) {
                    if viewModel.isLoading {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    } else {
                        Text("Buscar")
                    }
                }
                .disabled(!viewModel.isSearchEnabled || viewModel.isLoading)
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.blue)
                .foregroundColor(.white)
                .cornerRadius(8)
                .padding(.horizontal)
                
                if let cepInfo = viewModel.cepInfo {
                    VStack(spacing: 8) {
                        InfoRow(label: "CEP", value: cepInfo.cep)
                        InfoRow(label: "Logradouro", value: cepInfo.logradouro)
                        InfoRow(label: "Bairro", value: cepInfo.bairro)
                        InfoRow(label: "Cidade", value: cepInfo.localidade)
                        InfoRow(label: "UF", value: cepInfo.uf)
                    }
                    .padding()
                    .background(Color(.systemBackground))
                    .cornerRadius(8)
                    .shadow(radius: 4)
                    .padding(.horizontal)
                }
                
                Spacer()
            }
            .padding(.top)
            .alert("Erro", isPresented: $showingError) {
                Button("OK", role: .cancel) { }
            } message: {
                Text(viewModel.error ?? "Erro desconhecido")
            }
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
            Spacer()
            Text(value)
        }
    }
}

@MainActor
class CepViewModelWrapper: ObservableObject {
    private let viewModel = CepViewModel()
    @Published var cep: String = "" {
        didSet {
            viewModel.onCepChanged(cep: cep)
        }
    }
    @Published var isLoading = false
    @Published var isInputEnabled = true
    @Published var isSearchEnabled = false
    @Published var error: String?
    @Published var cepInfo: CepInfo?
    
    init() {
        observeState()
    }
    
    private func observeState() {
        Task {
            for await state in viewModel.uiState {
                isLoading = state.isLoading
                isInputEnabled = state.isInputEnabled
                isSearchEnabled = state.isSearchEnabled
                error = state.error
                cepInfo = state.cepInfo
            }
        }
    }
    
    func buscarCep() async {
        await viewModel.buscarCep()
    }
}

struct CepView_Previews: PreviewProvider {
    static var previews: some View {
        CepView()
    }
} 