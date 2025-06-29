Minimal CodingChapters Plugin provides a
simple plugin to discuss verification results
of https://plugins.jetbrains.com/plugin/24114-codebook/edit/versions/stable/753094
that were generated when uploading codebook plugin to marketplace

---

<ins>Build Plugin :-</ins>

`sbt clean compile packageArtifact`

---

<ins>Upload Plugin :-</ins>

upload generated zip inside `target` directory   
to marketplace for verification

---

also, for debugging, you may have to add below to run configuration vm options :-

`--add-opens=java.desktop/javax.swing.text.html.parser=ALL-UNNAMED`