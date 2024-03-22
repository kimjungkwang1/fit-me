module.exports = {
  devServer: {
    host: '0.0.0.0',
    port: 3000,
    hot: true,
    liveReload: true,
    client: {
      logging: 'none',
      overlay: {
        errors: true,
        warnings: false,
      },
      reconnect: 10,
    },
    devMiddleware: {
      publicPath: '/ws',
    },
  },
};
