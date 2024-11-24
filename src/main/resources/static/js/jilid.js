$(document).ready(function () {
  $("#tabel-jilid").DataTable({
    ajax: {
      method: "GET",
      url: "api/jilid",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      { data: "name" },
      { data: "kelas.name" },
      {
        data: null,
        render: (data) => {
          return `
                <div class="d-flex gap-3 justify-content-center">
                  <!-- Button trigger modal detail -->
                  <button
                    type="button"
                    class="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#detail"
                    onclick="getById(${data.id})"
                  >
                    Detail
                  </button>
                  <!-- Button trigger modal update -->
                  <button
                    type="button"
                    sec:authorize="hasRole('KEPALA')"
                    class="btn btn-warning"
                    data-bs-toggle="modal"
                    data-bs-target="#update"
                    onclick="beforeUpdate(${data.id})"
                  >
                    Update
                  </button>
                  <!-- Button trigger modal delete -->
                  <button
                    type="button"
                    sec:authorize="hasRole('KEPALA')"
                    class="btn btn-danger"
                    onclick="deleteRegion(${data.id})"
                  >
                    Delete
                  </button>
                </div>
              `;
        },
      },
    ],
  });
});
