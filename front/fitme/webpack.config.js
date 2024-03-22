module.exports = {
  devServer: {
    port: 3000,
    liveReload: true,
    host: 'fit-me.site',
    allowedHosts: 'all',
    open: true,
    client: {
      overlay: true,
      webSocketURL: 'ws://fit-me.site/ws',
    },
    compress: true,
  },
};
