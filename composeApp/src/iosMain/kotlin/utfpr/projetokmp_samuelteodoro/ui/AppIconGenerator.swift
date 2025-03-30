import SwiftUI

struct AppIconGenerator {
    static func generateIcon(size: CGSize) -> UIImage {
        let renderer = UIGraphicsImageRenderer(size: size)
        
        return renderer.image { context in
            // Fundo verde
            UIColor(hex: "009C3B").setFill()
            context.fill(CGRect(origin: .zero, size: size))
            
            // Losango amarelo
            let path = UIBezierPath()
            path.move(to: CGPoint(x: size.width * 0.5, y: 0))
            path.addLine(to: CGPoint(x: size.width, y: size.height * 0.5))
            path.addLine(to: CGPoint(x: size.width * 0.5, y: size.height))
            path.addLine(to: CGPoint(x: 0, y: size.height * 0.5))
            path.close()
            UIColor(hex: "FFDF00").setFill()
            path.fill()
            
            // Círculo azul
            let circleSize = size.width * 0.4
            let circleRect = CGRect(
                x: (size.width - circleSize) / 2,
                y: (size.height - circleSize) / 2,
                width: circleSize,
                height: circleSize
            )
            let circle = UIBezierPath(ovalIn: circleRect)
            UIColor(hex: "002776").setFill()
            circle.fill()
            
            // Faixas brancas
            for i in 0..<27 {
                let angle = CGFloat(i) * (360.0 / 27.0)
                let transform = CGAffineTransform(translationX: size.width/2, y: size.height/2)
                    .rotated(by: angle * .pi / 180)
                    .translatedBy(x: -size.width/2, y: -size.height/2)
                
                let stripe = UIBezierPath(rect: CGRect(
                    x: size.width/2 - size.width * 0.01,
                    y: 0,
                    width: size.width * 0.02,
                    height: size.height * 0.5
                ))
                stripe.apply(transform)
                UIColor.white.setFill()
                stripe.fill()
            }
            
            // Estrelas
            let stars = [
                (0.5, 0.5, 0.15), // Centro
                (0.2, 0.2, 0.08), // Norte
                (0.8, 0.2, 0.08), // Nordeste
                (0.8, 0.8, 0.08), // Sudeste
                (0.2, 0.8, 0.08)  // Sul
            ]
            
            for (x, y, size) in stars {
                let starSize = size * size.width
                let starRect = CGRect(
                    x: x * size.width - starSize/2,
                    y: y * size.height - starSize/2,
                    width: starSize,
                    height: starSize
                )
                drawStar(in: starRect, points: 5, innerRatio: 0.4)
            }
        }
    }
    
    private static func drawStar(in rect: CGRect, points: Int, innerRatio: CGFloat) {
        let center = CGPoint(x: rect.midX, y: rect.midY)
        let radius = min(rect.width, rect.height) / 2
        let innerRadius = radius * innerRatio
        
        let path = UIBezierPath()
        let angle = (2 * .pi) / CGFloat(points * 2)
        
        for i in 0..<points * 2 {
            let currentRadius = i % 2 == 0 ? radius : innerRadius
            let x = center.x + currentRadius * cos(CGFloat(i) * angle - .pi / 2)
            let y = center.y + currentRadius * sin(CGFloat(i) * angle - .pi / 2)
            
            if i == 0 {
                path.move(to: CGPoint(x: x, y: y))
            } else {
                path.addLine(to: CGPoint(x: x, y: y))
            }
        }
        
        path.close()
        UIColor.white.setFill()
        path.fill()
    }
}

// Extensão para suportar cores hexadecimais no UIKit
extension UIColor {
    convenience init(hex: String) {
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
            red: CGFloat(r) / 255,
            green: CGFloat(g) / 255,
            blue: CGFloat(b) / 255,
            alpha: CGFloat(a) / 255
        )
    }
} 