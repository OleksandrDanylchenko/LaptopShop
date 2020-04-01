<link rel="stylesheet" href="/styles/navbarStyle.css">

<nav class="navbar navbar-expand-xl bg-light">
    <a class="navbar-brand" href="/">
        <img width="100px" height="60px" src="/navbarLogo.png" alt="navbarLogo">
    </a>
    <button class="navbar-toggler navbar-light" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <#assign controllers = ['availability', 'basket', 'buying', 'client', 'cpu', 'display', 'employee', 'gpu',
    'hardware', 'hdd', 'label', 'laptop', 'ram', 'shop', 'ssd', 'type']>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#list controllers as controller>
                <li class="nav-item">
                    <a class="nav-link" href="/${controller}">${controller?cap_first}</a>
                </li>
            </#list>
        </ul>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <a class="btn btn-outline-dark" href="/login" role="button"><i class="fas fa-user-cog"></i> Увійти</a>
            </div>
        </div>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <form action="/logout" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-outline-dark"><i class="fas fa-user-cog"></i> Вийти</button>
                </form>
            </div>
        </div>
    </div>
</nav>