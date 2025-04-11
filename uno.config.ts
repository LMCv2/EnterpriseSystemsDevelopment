import { defineConfig } from "unocss";
import presetWind4 from '@unocss/preset-wind4'
import presetTypography from '@unocss/preset-typography'
import presetIcons from '@unocss/preset-icons'

export default defineConfig({
  presets: [
    presetWind4(),
    presetTypography(),
    presetIcons()
  ],
  content: {
    pipeline: {
      include: ["src/**/*.jsp"],
    },
  },
});
