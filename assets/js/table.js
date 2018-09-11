import axios from "axios";
var app = new Vue({
  el: "#product-app",
  data: {
    products: [], // data in table
    searchResults: [],
    paginatedProducts: [],
    search_term: "",
    current_page: 1,
    total_pages: 1,
    pagination_base_url: "http://localhost:4000/api/v1/products?page=",
    url: "http://localhost:4000/api/v1/products",
    searchUrl: "http://localhost:4000/api/v1/products?query=",
    firstDisabled: false,
    errors: []
  },

  created() {
    axios
      .get(this.url)
      .then(response => {
        this.paginatedProducts = response.data.data;
        this.products = this.paginatedProducts;
        this.total_pages = parseInt(response.headers["total-pages"]);
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    last: function() {
      axios
        .get(this.pagination_base_url + this.total_pages)
        .then(response => {
          this.paginatedProducts = response.data.data;
          this.current_page = this.total_pages;
        })
        .catch(error => console.log(error));
    },
    previous: function() {
      this.current_page--;
      if (this.current_page <= 0) {
        this.current_page = 1;
      }
      this.getPage(this.current_page);
    },
    next: function() {
      this.current_page++;
      if (this.current_page >= this.total_pages) {
        this.current_page = this.total_pages;
      }
      this.getPage(this.current_page);
    },
    first: function() {
      this.current_page = 1;
      this.getPage(this.current_page);
    },
    getPage: function(page) {
      axios
        .get(this.pagination_base_url + this.current_page)
        .then(response => {
          this.paginatedProducts = response.data.data;
        })
        .catch(error => console.log(error));
    },
    onSubmit: function() {
      if (this.search_term.length > 0) {
        axios
          .post(this.searchUrl + this.search_term)
          .then(response => {
            this.searchResults = response.data.data;
          })
          .catch(error => console.log(error));
      } else {
        this.errors.push("Search term is required.");
      }

      // Check if the search retruned any results
      if (this.searchResults.length == 0) {
        this.errors.push("Search result yielded no results.");
      }
    }
  },
  computed: {
    filteredProducts: function() {
      return this.products.filter(product => {
        return (
          product.name.toLowerCase().match(this.search_term) ||
          product.nsn.toString().match(this.search_term) ||
          product.nsn_formatted.match(this.search_term)
        );
      });
    },

    displayProducts: function() {
      if(this.search_term.length === 0) {
        this.products = this.paginatedProducts;
      }
      else {
        if(this.searchResults.length > 0) {
          this.products = this.searchResults;
        }
        else {
          this.errors.push("Search Result yielded no results.");
          this.errors.push("Clear the search input field to clear the search result.");
        }
      }
      return this.products;
    },
  }
});
