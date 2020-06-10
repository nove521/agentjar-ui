module.exports = {
    devServer: {
        proxy: {
            '/rest': {
                target: 'http://127.0.0.1:18088',
                changeOrigin: true,
                pathRewrite: {
                    '^/rest': '/rest'
                }
            },
            '/login': {
                target: 'http://127.0.0.1:18088',
                changeOrigin: true,
                pathRewrite: {
                    '^/login': '/login'
                }
            }
        }
    }
}