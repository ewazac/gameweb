import Browse from '../views/admin/contest/browse'
import EditAdd from '../views/admin/contest/edit-add'
import Competitions from '../components/Competitions';
import SingleCompetition from '../components/SingleCompetition';
export default [
    {
        path:'/admin/contests',
        name: 'Konkursy',
        component: Browse,
        meta:{auth: true, hideBreadcrumbs: true}
    },
    {
        path:'/admin/contest/create',
        name: 'Konkursy',
        component: EditAdd,
        meta:{auth: true, hideBreadcrumbs: true}
    },
    {
        path: '/competitions',
        name: 'Konkursy',
        component: Competitions,
        meta:{auth:true}
    },
    {
        path: '/competitions/:id',
        name: 'Konkurs',
        component: SingleCompetition,
        meta:{auth:true}
    }
]
