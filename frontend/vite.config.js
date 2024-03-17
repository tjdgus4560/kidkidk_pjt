import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    assetsInclude: ['**/*.PNG'],
    resolve: {
        alias: {
            '@images': '/src/assets/images',
            '@components': '/src/components',
            '@configs': '/src/configs',
            // 다른 별칭들 추가 가능
            '@api': '/src/apis/api',
            '@util': '/src/apis/util',
            '@store': '/src/store',
        },
    },

    // server: {
    //     proxy: {
    //         '/api': {
    //             target: 'http://localhost:8080',
    //             changeOrigin: true,
    //             rewrite: (path) => path.replace(/^\/api/, ''),
    //         },
    //     },
    // },
});
