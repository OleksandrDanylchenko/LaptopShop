<nav class="navbar navbar-expand-xl bg-light">
    <a class="navbar-brand" href="/">
        <img width="100px" height="60px" src="/navbarLogo.png" alt="navbarLogo">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <#assign controllers = ['availability', 'basket', 'buying', 'client', 'cpu', 'display', 'employee', 'gpu',
    'hardware', 'hdd', 'laptop', 'label', 'ram', 'shop', 'ssd', 'type']>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#list controllers as controller>
                <li class="nav-item">
                    <a class="nav-link" href="/${controller}">${controller?cap_first}</a>
                </li>
            </#list>
        </ul>
    </div>
</nav>