<template>
  <div class="container">
    <nav class="navbar">
      <ul class="navbar-nav">

        <li class="logo">
          <router-link class="nav-link" to="/">
            <logo/>
            <span class="link-text logo-text">Logo</span>
          </router-link>
        </li>

        <li class="nav-item">
          <router-link class="nav-link" to="/dashboard">
            <dashboard-icon/>
            <span class="link-text">Dashboard</span>
          </router-link>
        </li>

        <li class="nav-item">
          <router-link class="nav-link" to="/repository">
            <docker-icon/>
            <span class="link-text">Repository</span>
          </router-link>
        </li>

        <li class="nav-item">
          <router-link class="nav-link" to="/k8s">
            <kubernetes-icon/>
            <span class="link-text">Kubernetes</span>
          </router-link>
        </li>

        <li class="nav-item">
          <router-link class="nav-link" to="/settings">
            <settings-icon/>
            <span class="link-text">Settings</span>
          </router-link>
        </li>

        <li id="themeButton" class="nav-item">
          <a class="nav-link" href="#">

          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import SettingsIcon from "./svgs/SettingsIcon";
import DockerIcon from "./svgs/DockerIcon";
import KubernetesIcon from "./svgs/KubernetesIcon";
import DashboardIcon from "./svgs/DashboardIcon";
import Logo from "./svgs/Logo";
export default {
  components: {Logo, DashboardIcon, KubernetesIcon, DockerIcon, SettingsIcon}
}
</script>