#!/bin/bash

# Compilar e executar o script Swift
swiftc composeApp/src/iosMain/kotlin/utfpr/projetokmp_samuelteodoro/ui/AppIconGenerator.swift composeApp/src/iosMain/kotlin/utfpr/projetokmp_samuelteodoro/ui/IconGenerator.swift -o generate_icons
./generate_icons

# Limpar arquivos temporários
rm generate_icons

echo "Ícones gerados com sucesso!" 