$(document).ready(function () {
  $("#tabel-progress").DataTable({
    ajax: {
      method: "GET",
      url: "api/progress/santri",
      dataSrc: function (data) {
        console.log("Received Data: ", data);

        return Object.keys(data).map((key) => data[key][0]);
      },
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      {
        data: "santri.name",
      },
      {
        data: "santri.jilid.name",
      },
      {
        data: null,
        render: (data) => {
          return `
              <div class="d-flex gap-3 justify-content-center">
                <!-- Button to navigate and call getById -->
                <button
                  type="button"
                  class="btn btn-primary"
                  onclick="navigateAndFetch(${data.santri.id})"
                >
                  Detail
                </button>
              </div>
            `;
        },
      },
    ],
  });
});

function navigateAndFetch(id) {
  window.location.href = "/progress/santri/" + id;
  getById(id);
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $("#tabel-detail-progress").DataTable({
    ajax: {
      method: "GET",
      url: "api/progress/santri/",
      dataSrc: function (data) {
        console.log("Received Data: ", data);

        return Object.values(data).flat();
      },
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      {
        data: "santri.name",
      },
      {
        data: "santri.jilid.name",
      },
      {
        data: "santri.name",
      },
    ],
  });
}
