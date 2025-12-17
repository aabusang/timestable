# Modulo Times Table Visualization

An interactive JavaFX application that visualizes patterns emerging from times tables using modulo arithmetic on a circular path. Features a modern interface with preset patterns, keyboard shortcuts, and dark mode.

## Features

- **Preset Patterns** - One-click access to famous patterns (Cardioid, Nephroid, etc.)
- **Interactive Controls** - Real-time sliders for times table number, increment, and delay
- **Keyboard Shortcuts** - Full keyboard navigation and control
- **Color Customization** - Full color picker for line colors
- **Dark Mode** - Toggle between light and dark themes
- **Export** - Save visualizations as PNG images
- **Animation Controls** - Play, pause, stop, and reset
- **Live Information** - Pattern recognition and formula display
- **Smooth Transitions** - Fade-in effects for visual updates

## Usage

### Controls

**Playback:**
- ▶ **Play** - Start animation
- ⏸ **Pause** - Pause without resetting
- ⏹ **Stop** - Stop and reset
- ↻ **Reset** - Return to defaults

**Adjustment:**
- **Increment Slider** - How quickly the times table number changes (0.01-5.0)
- **Delay Slider** - Animation speed (0-2 seconds)
- **Jump Inputs** - Navigate to specific times table numbers and point counts
- **Color Picker** - Choose any color for the lines
- **Show Circle** - Toggle circle visibility

**Presets:**
- **Cardioid** (TTN: 2) - Heart-shaped curve
- **Nephroid** (TTN: 3) - Kidney-shaped curve
- **Epicycloid 4, 5** - Multi-cusped patterns
- **Pattern 29, 34, 51, 79, 99** - Various interesting patterns
- **π (Pi)** - Irrational pattern using π ≈ 3.14159
- **√2** - Irrational pattern using √2 ≈ 1.41421
- **φ (Phi)** - Golden ratio ≈ 1.61803
- **e (Euler)** - Euler's number ≈ 2.71828
- **√3** - Irrational pattern using √3 ≈ 1.73205

### Keyboard Shortcuts

| Key | Action |
|-----|--------|
| `Space` | Play/Pause |
| `R` | Reset |
| `S` | Save Image |
| `H` | Toggle Circle |
| `D` | Dark Mode |
| `↑/↓` | Adjust TTN by ±0.1 |
| `←/→` | Adjust TTN by ±1.0 |
| `1-9` | Load Presets 1-9 |
| `0` | Load Preset 10 (π) |

## Architecture

- **`Main`** - GUI setup, controls, and visualization orchestration
- **`PointOnCircle`** - Represents points on the circle with coordinate transformations
- **`Visualization`** - Generates modulo times table patterns by connecting circle points
- **`DecimalTextVerifier`** - Validates and formats decimal inputs

