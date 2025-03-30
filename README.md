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
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…# Projeto_UTFP_KMP
