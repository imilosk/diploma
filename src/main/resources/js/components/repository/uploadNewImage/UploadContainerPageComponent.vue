<template>
  <div>
    <nav-bar-component/>
    <div class="md:grid md:grid-cols-2 md:gap-6">
      <div class="mt-5 md:mt-0 md:col-span-1">
        <form action="#" method="POST">
          <div class="shadow sm:rounded-md sm:overflow-hidden">
            <div class="px-4 py-5 bg-white space-y-6 sm:p-6">
              <div class="grid grid-cols-3 gap-6">
                <div class="col-span-3 sm:col-span-2">
                  <label for="company_website" class="block text-sm font-medium text-gray-700">
                    Website
                  </label>
                  <div class="mt-1 flex rounded-md shadow-sm">
                    <span class="inline-flex items-center px-3 rounded-l-md border border-r-0 border-gray-300 bg-gray-50 text-gray-500 text-sm">
                      username/
                    </span>
                    <input type="text" name="company_website" id="company_website" class="focus:ring-indigo-500 focus:border-indigo-500 flex-1 block w-full rounded-none rounded-r-md sm:text-sm border-gray-300" placeholder="www.example.com" />
                  </div>
                  <label class="block text-sm font-medium text-gray-700">
                    Expire timedelta with seconds:
                  </label>
                  <input
                      type="text"
                      name="exp"
                      id="exp"
                      placeholder='100'
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                  />
                  <input
                      type="number"
                      name="exp"
                      id="ex1p"
                      placeholder='100'
                      class="mt-1 mb-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                  />
                  <button
                      type="submit"
                      class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-green-600"
                  >
                    Submit
                  </button>
                  <button
                      type="submit"
                      class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-300 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 text-green-600"
                      disabled
                  >
                    <svg
                        class="animate-spin h-4 w-4 rounded-full bg-transparent border-2 border-transparent border-opacity-50 mr-2" style="border-right-color: white; border-top-color: white;" viewBox="0 0 24 24"></svg>
                    Loading
                  </button>
                </div>
              </div>
            </div>
          </div>

        </form>
      </div>

      <div class="mt-5 md:mt-0 md:col-span-1">
        <form action="#" method="POST">
          <div class="shadow sm:rounded-md sm:overflow-hidden">
            <div class="px-4 py-5 bg-white space-y-6 sm:p-6">
              <file-upload-component/>
            </div>
          </div>

        </form>
      </div>

    </div>
  </div>
</template>

<script>
import NavBarComponent from "../../dashboard/NavBarComponent";
export default {
  components: {NavBarComponent}
}
</script>