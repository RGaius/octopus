import type { ProLayoutProps } from '@ant-design/pro-components';

/**
 * @name
 */
const Settings: ProLayoutProps & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: "light",
  colorPrimary: "#1890ff",
  title: 'Octopus',
  layout: "top",
  contentWidth: "Fluid",
  fixedHeader: true,
  fixSiderbar: true,
  pw: true,
  token: {},
  splitMenus: false,
  siderMenuType: "sub",
  footerRender: false
};

export default Settings;
