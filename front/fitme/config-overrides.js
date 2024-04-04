module.exports = function override(config, env) {
  config.module.rules = config.module.rules.map((rule) => {
    if (rule.oneOf instanceof Array) {
      return {
        ...rule,
        oneOf: rule.oneOf.map((oneOf) => {
          if (oneOf.test && oneOf.test.toString().includes('js|mjs')) {
            return {
              ...oneOf,
              resolve: {
                ...oneOf.resolve,
                fullySpecified: false,
              },
            };
          }

          return oneOf;
        }),
      };
    }

    return rule;
  });

  return config;
};
