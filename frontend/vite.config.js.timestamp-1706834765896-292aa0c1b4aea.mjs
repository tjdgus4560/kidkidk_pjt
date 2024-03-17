// vite.config.js
import { defineConfig } from "file:///C:/ssafy/projects/S10P12B305/frontend/node_modules/vite/dist/node/index.js";
import react from "file:///C:/ssafy/projects/S10P12B305/frontend/node_modules/@vitejs/plugin-react/dist/index.mjs";
var vite_config_default = defineConfig({
  plugins: [react()],
  assetsInclude: ["**/*.PNG"],
  resolve: {
    alias: {
      "@images": "/src/assets/images",
      "@components": "/src/components"
      // 다른 별칭들 추가 가능
    }
  },
  server: {
    proxy: {
      "/api": {
        // front 서버를 spring 서버로 바꿔주기
        // http://localhost/api/board
        target: "http://localhost",
        // /api를 빈칸으로 대체
        rewrite: (path) => path.replace(/^\/api/, ""),
        // 직접 공공데이터를 가져오는 경우 origin 변경
        changeOrigin: true
      },
      "/data": {
        target: "http://openapi.molit.go.kr:8081",
        // pathRewrite: { "^/data": "" },
        rewrite: (path) => path.replace(/^\/data/, ""),
        changeOrigin: true
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJDOlxcXFxzc2FmeVxcXFxwcm9qZWN0c1xcXFxTMTBQMTJCMzA1XFxcXGZyb250ZW5kXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJDOlxcXFxzc2FmeVxcXFxwcm9qZWN0c1xcXFxTMTBQMTJCMzA1XFxcXGZyb250ZW5kXFxcXHZpdGUuY29uZmlnLmpzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9DOi9zc2FmeS9wcm9qZWN0cy9TMTBQMTJCMzA1L2Zyb250ZW5kL3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSBcInZpdGVcIjtcclxuaW1wb3J0IHJlYWN0IGZyb20gXCJAdml0ZWpzL3BsdWdpbi1yZWFjdFwiO1xyXG5cclxuLy8gaHR0cHM6Ly92aXRlanMuZGV2L2NvbmZpZy9cclxuZXhwb3J0IGRlZmF1bHQgZGVmaW5lQ29uZmlnKHtcclxuICBwbHVnaW5zOiBbcmVhY3QoKV0sXHJcbiAgYXNzZXRzSW5jbHVkZTogW1wiKiovKi5QTkdcIl0sXHJcbiAgcmVzb2x2ZToge1xyXG4gICAgYWxpYXM6IHtcclxuICAgICAgXCJAaW1hZ2VzXCI6IFwiL3NyYy9hc3NldHMvaW1hZ2VzXCIsXHJcbiAgICAgIFwiQGNvbXBvbmVudHNcIjogXCIvc3JjL2NvbXBvbmVudHNcIixcclxuICAgICAgLy8gXHVCMkU0XHVCOTc4IFx1QkNDNFx1Q0U2RFx1QjRFNCBcdUNEOTRcdUFDMDAgXHVBQzAwXHVCMkE1XHJcbiAgICB9LFxyXG4gIH0sXHJcblxyXG4gIHNlcnZlcjoge1xyXG4gICAgcHJveHk6IHtcclxuICAgICAgXCIvYXBpXCI6IHtcclxuICAgICAgICAvLyBmcm9udCBcdUMxMUNcdUJDODRcdUI5N0Mgc3ByaW5nIFx1QzExQ1x1QkM4NFx1Qjg1QyBcdUJDMTRcdUFGRDRcdUM4RkNcdUFFMzBcclxuICAgICAgICAvLyBodHRwOi8vbG9jYWxob3N0L2FwaS9ib2FyZFxyXG4gICAgICAgIHRhcmdldDogXCJodHRwOi8vbG9jYWxob3N0XCIsXHJcbiAgICAgICAgLy8gL2FwaVx1Qjk3QyBcdUJFNDhcdUNFNzhcdUM3M0NcdUI4NUMgXHVCMzAwXHVDQ0I0XHJcbiAgICAgICAgcmV3cml0ZTogKHBhdGgpID0+IHBhdGgucmVwbGFjZSgvXlxcL2FwaS8sIFwiXCIpLFxyXG4gICAgICAgIC8vIFx1QzlDMVx1QzgxMSBcdUFDRjVcdUFDRjVcdUIzNzBcdUM3NzRcdUQxMzBcdUI5N0MgXHVBQzAwXHVDODM4XHVDNjI0XHVCMjk0IFx1QUNCRFx1QzZCMCBvcmlnaW4gXHVCQ0MwXHVBQ0JEXHJcbiAgICAgICAgY2hhbmdlT3JpZ2luOiB0cnVlLFxyXG4gICAgICB9LFxyXG4gICAgICBcIi9kYXRhXCI6IHtcclxuICAgICAgICB0YXJnZXQ6IFwiaHR0cDovL29wZW5hcGkubW9saXQuZ28ua3I6ODA4MVwiLFxyXG4gICAgICAgIC8vIHBhdGhSZXdyaXRlOiB7IFwiXi9kYXRhXCI6IFwiXCIgfSxcclxuICAgICAgICByZXdyaXRlOiAocGF0aCkgPT4gcGF0aC5yZXBsYWNlKC9eXFwvZGF0YS8sIFwiXCIpLFxyXG4gICAgICAgIGNoYW5nZU9yaWdpbjogdHJ1ZSxcclxuICAgICAgfSxcclxuICAgIH0sXHJcbiAgfSxcclxufSk7XHJcbiJdLAogICJtYXBwaW5ncyI6ICI7QUFBMlMsU0FBUyxvQkFBb0I7QUFDeFUsT0FBTyxXQUFXO0FBR2xCLElBQU8sc0JBQVEsYUFBYTtBQUFBLEVBQzFCLFNBQVMsQ0FBQyxNQUFNLENBQUM7QUFBQSxFQUNqQixlQUFlLENBQUMsVUFBVTtBQUFBLEVBQzFCLFNBQVM7QUFBQSxJQUNQLE9BQU87QUFBQSxNQUNMLFdBQVc7QUFBQSxNQUNYLGVBQWU7QUFBQTtBQUFBLElBRWpCO0FBQUEsRUFDRjtBQUFBLEVBRUEsUUFBUTtBQUFBLElBQ04sT0FBTztBQUFBLE1BQ0wsUUFBUTtBQUFBO0FBQUE7QUFBQSxRQUdOLFFBQVE7QUFBQTtBQUFBLFFBRVIsU0FBUyxDQUFDLFNBQVMsS0FBSyxRQUFRLFVBQVUsRUFBRTtBQUFBO0FBQUEsUUFFNUMsY0FBYztBQUFBLE1BQ2hCO0FBQUEsTUFDQSxTQUFTO0FBQUEsUUFDUCxRQUFRO0FBQUE7QUFBQSxRQUVSLFNBQVMsQ0FBQyxTQUFTLEtBQUssUUFBUSxXQUFXLEVBQUU7QUFBQSxRQUM3QyxjQUFjO0FBQUEsTUFDaEI7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
