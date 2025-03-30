# Consulta CEP

Aplicativo multiplataforma (Android e iOS) para consulta de CEP utilizando a API ViaCEP.

## Autor
Samuel Teodoro Ferreira

## Funcionalidades
- Consulta de CEP
- Exibição de informações detalhadas do endereço (logradouro, bairro, cidade e UF)
- Interface moderna e intuitiva
- Validação de entrada de CEP
- Tratamento de erros
- Indicador de carregamento

## Tecnologias Utilizadas
- Kotlin Multiplatform
- Jetpack Compose (Android)
- SwiftUI (iOS)
- Ktor Client
- Material Design 3
- Coroutines
- StateFlow

## Como Executar

### Android
1. Abra o projeto no Android Studio
2. Selecione o módulo `composeApp`
3. Execute o aplicativo em um emulador ou dispositivo Android

### iOS
1. Abra o projeto no Xcode
2. Selecione o target `iosApp`
3. Execute o aplicativo em um simulador ou dispositivo iOS

## Estrutura do Projeto
- `shared/`: Código compartilhado entre Android e iOS
  - `model/`: Classes de modelo
  - `network/`: Cliente HTTP e chamadas de API
  - `viewmodel/`: ViewModels compartilhados
- `composeApp/`: Aplicativo Android
  - `ui/`: Componentes de interface do usuário
- `iosApp/`: Aplicativo iOS
  - `CepView.swift`: Interface do usuário iOS

## Licença
Prrojeto Free para download pois sou militante do movimento Open Source.
