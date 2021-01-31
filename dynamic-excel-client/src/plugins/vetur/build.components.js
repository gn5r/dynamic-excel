const files = require.context(`../../components/common`, true, /.*\.vue$/);

let components = {};
Object.values(files.keys()).forEach((key) => {
  const component = files(key).default;
  const name =
    component.name ||
    component
      .split("/")
      .pop()
      .replace(/\.\w+$/, "");
  components[name] = component;
});

export default components;
