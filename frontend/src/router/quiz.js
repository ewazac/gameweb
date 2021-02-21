import Browse from '../views/admin/quiz/index'
import EditAdd from '../views/admin/quiz/edit-add'
export default [

    {
        path: '/admin/quiz',
        name: 'Quiz',
        component: Browse,
        meta:{auth: true}
    },
    {
        path: '/admin/quiz/:id',
        name: 'Quiz',
        component: EditAdd,
        meta:{auth: true}
    },
    {
        path: '/admin/quiz/create',
        name: 'Quiz',
        component: EditAdd,
        meta:{auth: true}
    },
]