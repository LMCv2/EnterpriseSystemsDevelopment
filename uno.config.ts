import { defineConfig } from "unocss";
import presetWind4 from '@unocss/preset-wind4'
import presetTypography from '@unocss/preset-typography'
import presetIcons from '@unocss/preset-icons'
import { presetHeroPatterns } from '@julr/unocss-preset-heropatterns'

export default defineConfig({
  presets: [
    presetWind4(),
    presetTypography(),
    presetIcons(),
    presetHeroPatterns()
  ],
  content: {
    pipeline: {
      include: ["src/**/*.jsp"],
    },
  },
});
