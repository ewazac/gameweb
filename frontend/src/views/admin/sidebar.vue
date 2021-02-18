<template>
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading">Nawigacja </div>
        <div class="list-group list-group-flush">
            <router-link :to="'/account'" class="list-group-item list-group-item-action bg-light">Account</router-link>
            <router-link :to="'/admin/news'" class="list-group-item list-group-item-action bg-light">Newsy</router-link>
            <a href="#" class="list-group-item list-group-item-action bg-light">Quizy</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Konkursy</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Polecane</a>
        </div>
    </div>
</template>