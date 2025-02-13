/* refer for more details https://tailwindcss.com/docs/configuration */

module.exports = {
  content: ['src/**/*.{html,js, jsx, tsx, ts}'],
  plugins: [
    require('@tailwindcss/forms'),
  ],
  safelist: [
    'bg-red-100',
    'bg-red-500',
    'bg-blue-500',
    'text-red-600',
    'border-red-500',
    'border-blue-500',
    'border-amber-500',
    'border-emerald-500',
  ],
  corePlugins: {
    //outline: false,
  },
};
