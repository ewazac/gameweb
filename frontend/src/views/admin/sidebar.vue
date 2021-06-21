<template>
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading">Nawigacja </div>
        <div class="list-group list-group-flush">
            <router-link :to="'/account'" class="list-group-item list-group-item-action bg-light">Account</router-link>
            <router-link :to="'/admin/news'" class="list-group-item list-group-item-action bg-light">Newsy</router-link>
            <router-link :to="'/admin/quiz'" class="list-group-item list-group-item-action bg-light">Quizy</router-link>
            <router-link :to="'/admin/contests'" class="list-group-item list-group-item-action bg-light">Konkursy</router-link>
            <router-link :to="'/admin/reviewstoaccept'" class="list-group-item list-group-item-action bg-light">Moderuj recenzje</router-link>
        </div>
    </div>
</template>
