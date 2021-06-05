<template>
  <div class="w-full">
    <nav class="relative flex flex-wrap items-center justify-between bg-white-500 rounded shadow-md">
      <div class="container px-4 mx-auto flex flex-wrap items-center justify-between">
        <div
            class="lg:flex flex-grow items-center flex"
            id="example-navbar-info"
        >
          <p class="px-3 py-2 flex items-center font-mono text-sm uppercase font-bold leading-snug text-purple-400 hover:opacity-75">
          </p>
          <p class="px-3 py-2 flex items-center font-mono text-sm uppercase font-bold leading-snug text-purple-400 hover:opacity-75">
          </p>
          <p class="px-3 py-2 flex items-center font-mono text-sm uppercase font-bold leading-snug text-purple-400 hover:opacity-75">
          </p>
          <p class="px-3 py-2 flex items-center font-mono text-sm uppercase font-bold leading-snug text-purple-400 hover:opacity-75">
          </p>
          <ul class="flex flex-col lg:flex-row list-none lg:ml-auto">
            <li class="nav-item">
              <p class="mt-1 px-3 py-2 flex items-center text-sm font-bold leading-snug text-black hover:opacity-75">
                User
              </p>
            </li>
            <li class="nav-item">
              <div class="px-3 py-2 flex items-center text-xs uppercase font-bold leading-snug text-black hover:opacity-75">
                Button
              </div>
            </li>
            <li class="nav-item">
              <div
                  to="/dashboard/profile-settings/"
                  class="px-3 py-2 flex items-center text-xs uppercase font-bold leading-snug text-black hover:opacity-75"
              >
                <i class="fas fa-globe text-lg leading-lg text-black opacity-75">
                  Icon
                </i>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "NavBarComponent"
}
</script>