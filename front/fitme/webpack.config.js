module.exports = {
  devServer: {
    port: 3000,
    liveReload: true,
    host: 'fit-me.site',
    allowedHosts: 'all',
    open: true,
    client: {
      overlay: true,
      webSocketURL: 'wss://fit-me.site:443/ws',
    },
    compress: true,
  },
};
