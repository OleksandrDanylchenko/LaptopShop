<#macro delete id deletionLink>
    <div class="modal fade" id="deleteModal${id}" tabindex="-1"
         role="dialog" aria-labelledby="typeDeleteLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <img src="https://lh3.googleusercontent.com/proxy/whxF-MlKpIN8dOtC42zuMUyR7MifuNKXu3bGX9lTo9vpt1zWXZ-r9ymmg3guSHS152TBHuks-Eo_GS6foiM8RuLiUWBMO3lvlGgyp8cFeSwbuCKnHIbWo9DkkCSCgO2XcUfGvicqJdaRjOpbKZIkYYp-pg"
                                 class="rounded mx-auto d-block" alt="trashBin">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="h3 text-center">Ви впевненні, що бажаєте видалити даний запис?</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="h5 text-center">Надалі його не можна буде відновити.</div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Скасувати</button>
                    <a class="btn btn-danger" href="${deletionLink}" role="button">Видалити</a>
                </div>
            </div>
        </div>
    </div>
</#macro>