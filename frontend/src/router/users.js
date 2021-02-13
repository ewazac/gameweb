import ResetPassword from "../views/users/resetPassword";
const orders = {
    path: '/reset-password',
    name: 'Reset password',
    component: ResetPassword,
    meta:{auth: false}
};
export default orders;