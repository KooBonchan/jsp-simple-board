# JSP simple board

## Requirements

### Board List

- search board by title, content, writer
- single image upload per board, download available from board list view.
- direct download from board list
- download count

### Board View

- update, delete can only done by writer
  - verified by password.
- simple password with no encryption
- pageview and download count

### Others

- create board with writer and password specified. (without application signing)

---

### Adjustment

- change board per page using ajax
- prev, next board

## Models

![data definition](/assets/data.png)

## Challenges

- ajax download to update downloads count with ajax
