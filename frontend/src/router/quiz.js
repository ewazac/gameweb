import Browse from '../views/admin/quiz/index'
import EditAdd from '../views/admin/quiz/edit-add'
import Quizes from '../components/Quizes';
import Quiz from '../components/SingleQuiz';
export default [
    {
        path:'/quizy',
        name: 'Quizy',
        component: Quizes,
        meta:{auth: false}
    },
    {
        path:'/quizy/:id',
        name: 'Quizy',
        component: Quiz,
        meta:{auth: false}
    },
    {
        path: '/admin/quiz',
        name: 'Quiz',
        component: Browse,
        meta:{auth: true, hideBreadcrumbs: true}
    },
    {
        path: '/admin/quiz/:id',
        name: 'Quiz',
        component: EditAdd,
        meta:{auth: true, hideBreadcrumbs: true}
    },
    {
        path: '/admin/quiz/create',
        name: 'Quiz',
        component: EditAdd,
        meta:{auth: true, hideBreadcrumbs: true}
    },
]
