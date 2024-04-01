#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

void *imprimeNumeros(void *args) {
    for (int i = 0; i < 10; i++) {
        printf("%d\n", i);
        sleep(1);
    }
    return NULL;
}

void *imprimeNumerosDecrecente(void *args) {
    for (int i = 10; i > 0; i--) {
        printf("%d\n", i);
        sleep(1);
    }
    return NULL;
}

int main() {
    pthread_t thread_id[2];

    pthread_create(&thread_id[0], NULL, imprimeNumeros, NULL);
    pthread_create(&thread_id[1], NULL, imprimeNumerosDecrecente, NULL);
    pthread_join(thread_id[0], NULL);
    pthread_join(thread_id[1], NULL);

    return 0;
}