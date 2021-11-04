const main = {
    init: function () {
        let _this = this;

        $("#btn-convert").on("click", () => {
            _this.save();
        });
    },
    save: () => {
        let data = {
            originalUrl: $("#originalUrl").val(),
        };

        $.ajax({
            type: "POST",
            url: "/api/v1/urls",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
        }).done((data) => {
            alert("단축 완료!");
            $("#shortenedUrl").val(data.data.shortenedUrl);
        }).fail((error) => {
            alert(JSON.stringify(error));
        });
    }
};

main.init();