import NewsComponent from "../views/admin/news";
const admin_routes = {
    path: '/admin/news',
    name: 'news',
    component: NewsComponent,
    meta:{auth: true, hideBreadcrumbs: true}
};
export default admin_routes;
