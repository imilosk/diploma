module.exports = {
    purge: [
        './src/main/resources/js/components/*.vue',
    ],
    darkMode: false, // or 'media' or 'class'
    theme: {
        extend: {},
    },
    variants: {
        extend: {},
        animation: ['responsive', 'motion-safe', 'motion-reduce'],
    },
    plugins: [
        require('@tailwindcss/forms'),
    ],
}
