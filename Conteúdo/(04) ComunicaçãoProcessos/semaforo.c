// C program to demonstrate working of Semaphores
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
  
sem_t semaphore;
  
void* thread(void* arg)
{
    //wait
    sem_wait(&semaphore);
    printf("\nEntered..\n");
  
    //critical section
    sleep(4);
      
    //signal
    printf("\nJust Exiting...\n");
    sem_post(&semaphore);
}
  
  
int main()
{
    sem_init(&semaphore, 0, 2);
    pthread_t t1,t2;
    pthread_create(&t1,NULL,thread,NULL);
    sleep(2);
    pthread_create(&t2,NULL,thread,NULL);
    pthread_join(t1,NULL);
    pthread_join(t2,NULL);
    sem_destroy(&semaphore);
    return 0;
}