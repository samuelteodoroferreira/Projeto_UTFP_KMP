import Foundation
import UIKit

// Tamanhos de ícone necessários
let iconSizes = [
    (20, 2), (20, 3),  // iPhone 20pt
    (29, 2), (29, 3),  // iPhone 29pt
    (40, 2), (40, 3),  // iPhone 40pt
    (60, 2), (60, 3),  // iPhone 60pt
    (20, 1), (20, 2),  // iPad 20pt
    (29, 1), (29, 2),  // iPad 29pt
    (40, 1), (40, 2),  // iPad 40pt
    (76, 1), (76, 2),  // iPad 76pt
    (83.5, 2),         // iPad Pro 83.5pt
    (1024, 1)          // App Store
]

// Diretório de saída
let outputDirectory = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"

// Criar diretório se não existir
try? FileManager.default.createDirectory(
    atPath: outputDirectory,
    withIntermediateDirectories: true,
    attributes: nil
)

// Gerar ícones
for (size, scale) in iconSizes {
    let actualSize = CGFloat(size) * CGFloat(scale)
    let image = AppIconGenerator.generateIcon(size: CGSize(width: actualSize, height: actualSize))
    
    // Nome do arquivo
    let fileName: String
    if scale == 1 {
        fileName = "Icon-\(size).png"
    } else {
        fileName = "Icon-\(size)@\(scale)x.png"
    }
    
    // Salvar arquivo
    if let data = image.pngData() {
        let filePath = (outputDirectory as NSString).appendingPathComponent(fileName)
        try? data.write(to: URL(fileURLWithPath: filePath))
        print("Gerado: \(fileName)")
    }
}

print("Geração de ícones concluída!") 